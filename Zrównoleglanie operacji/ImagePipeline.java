package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImagePipeline {

    public static void main(String[] args) throws IOException, InterruptedException {
        String source = "D:\\193249\\tak\\img";
        String destination ="D:\\193249\\tak\\result";
        processImages(source, destination);
    }

    public static void processImages(String inputDirectory, String outputDirectory) throws IOException, InterruptedException {
        List<Path> files;
        Path source = Path.of(inputDirectory);
        try (Stream<Path> stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
        }

        int[] threadPoolSizes = {1, 2, 4, 8};
        for (int poolSize : threadPoolSizes) {
            processImagesWithThreadPool(files, outputDirectory, poolSize);
        }
    }

    private static void processImagesWithThreadPool(List<Path> files, String outputDirectory, int poolSize) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(poolSize);



            files.parallelStream()
                    .map(path -> {
                        try {
                            BufferedImage image = ImageIO.read(path.toFile());
                            return Pair.of(path.getFileName().toString(), image);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .filter(pair -> pair != null)
                    .forEach(pair -> pool.submit(() -> {
                        int width = pair.getRight().getWidth();
                        int height = pair.getRight().getHeight();
                        BufferedImage image = new BufferedImage(width, height, pair.getRight().getType());

                        for (int i = 0; i < width; i++) {
                            for (int j = 0; j < height; j++) {
                                int rgb = pair.getRight().getRGB(i, j);
                                image.setRGB(i, j, 2 * rgb);
                            }
                        }
                        try {
                            String filename = pair.getLeft();
                            Path outputPath = Path.of(outputDirectory, filename);
                            ImageIO.write(image, "jpg", outputPath.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));

            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            long endTime = System.currentTimeMillis();
            System.out.println("Thread pool size: " + poolSize + ", Execution time: " + (endTime - startTime) + "ms");


    }
}
