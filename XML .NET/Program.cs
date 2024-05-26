using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;
using System.Xml;
using System.Xml.Linq;
using System.Xml.XPath;
using System.Runtime.ConstrainedExecution;
using System.Text;


public class Engine
{
    public double Displacement { get; set; }
    public double HorsePower { get; set; }
    [XmlAttribute]
    public string Model { get; set; }

    public Engine() { }

    public Engine(double displacement, double horsePower, string model)
    {
        Displacement = displacement;
        HorsePower = horsePower;
        Model = model;
    }
}

[XmlType("car")]
public class Car
{
    public string Model { get; set; }
    [XmlElement("Motor")]
    public Engine Engine { get; set; }
    public int Year { get; set; }

    public Car() { }

    public Car(string model, Engine engine, int year)
    {
        Model = model;
        Engine = engine;
        Year = year;
    }
}

class Program
{
    static void Main(string[] args)
    {
        List<Car> myCars = new List<Car>(){
            new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
            new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
            new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
            new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
            new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
            new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
            new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
            new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
            new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
        };

        var query1 = myCars
            .Where(car => car.Model == "A6")
            .Select(car => new
            {
                engineType = car.Engine.Model == "TDI" ? "diesel" : "petrol",
                hppl = car.Engine.HorsePower / car.Engine.Displacement
            });

        foreach (var result in query1)
        {
            Console.WriteLine($"engineType: {result.engineType}, hppl: {result.hppl}");
        }

        var query2 = query1
            .GroupBy(item => item.engineType)
            .Select(group => new
            {
                EngineType = group.Key,
                AvgHPPL = group.Average(item => item.hppl)
            });

        foreach (var result in query2)
        {
            Console.WriteLine($"{result.EngineType}: {result.AvgHPPL}");
        }


        //Serializacja i deserializacja
        XmlSerializer serializer = new XmlSerializer(typeof(List<Car>), new XmlRootAttribute("cars"));

        using (StreamWriter streamWriter = new StreamWriter("CarsCollection.xml"))
        {
            serializer.Serialize(streamWriter, myCars);
        }

        List<Car> deserializedCars;
        using (StreamReader streamReader = new StreamReader("CarsCollection.xml"))
        {
            deserializedCars = (List<Car>)serializer.Deserialize(streamReader);
        }


        //XPath
        XElement rootNode = XElement.Load("CarsCollection.xml");
        string xpathExpression = "//car[motor/@Model != 'TDI']/motor/HorsePower";
        IEnumerable<XElement> selectedElements = rootNode.XPathSelectElements(xpathExpression);
        double totalHP = 0;
        int count = 0;
        foreach (XElement element in selectedElements)
        {
            totalHP += (double)element;
            count++;
        }

        double avgHP = totalHP / count;
        Console.WriteLine("\nPrzecietna moc samochodow o silnikach innnych niz TDI " +  avgHP.ToString());


        IEnumerable<string> models = rootNode.XPathSelectElements("//car/Model").Select(m => m.Value).Distinct();
        Console.WriteLine("\nModele samochodów bez powtórzeń:");
        foreach (string model in models)
        {
            Console.WriteLine(model);
        }


        // Generowanie XML z LINQ
        createXmlFromLinq(myCars);

        // Generowanie XHTML
        generateXHTMLTable(myCars);

        // Modyfikacja dokumentu XML
        modifyXmlDocument();
    }

    private static void createXmlFromLinq(List<Car> myCars)
    {
        IEnumerable<XElement> nodes = myCars.Select(car =>
            new XElement("car",
                new XElement("Model", car.Model),
                new XElement("Motor",
                    new XAttribute("Model", car.Engine.Model),
                    new XElement("Displacement", car.Engine.Displacement),
                    new XElement("HorsePower", car.Engine.HorsePower)
                ),
                new XElement("Year", car.Year)
            )
        );

        XElement rootNode = new XElement("cars", nodes);
        rootNode.Save("CarsFromLinq.xml");
    }

    private static void generateXHTMLTable(List<Car> myCars)
    {
        XDocument xhtmlDoc = new XDocument(
            new XElement("html",
                new XElement("body",
                    new XElement("table",
                        new XElement("tr",
                            new XElement("th", "Model"),
                            new XElement("th", "Engine Type"),
                            new XElement("th", "Horse Power"),
                            new XElement("th", "Displacement"),
                            new XElement("th", "Year")
                        ),
                        myCars.Select(car =>
                            new XElement("tr",
                                new XElement("td", car.Model),
                                new XElement("td", car.Engine.Model),
                                new XElement("td", car.Engine.HorsePower),
                                new XElement("td", car.Engine.Displacement),
                                new XElement("td", car.Year)
                            )
                        )
                    )
                )
            )
        );

        xhtmlDoc.Save("CarsTable.html");
    }

    private static void modifyXmlDocument()
    {
        XDocument xmlDoc = XDocument.Load("CarsCollection.xml");
        foreach (XElement carElement in xmlDoc.Descendants("car"))
        {
            carElement.Element("Motor").Element("HorsePower").Name = "hp";
            carElement.Element("Model").Add(new XAttribute("Year", carElement.Element("Year").Value));
            carElement.Element("Year").Remove();
        }

        xmlDoc.Save("ModifiedCarsCollection.xml");
    }
}
