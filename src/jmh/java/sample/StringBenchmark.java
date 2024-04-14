package sample;


import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class StringBenchmark {
	
    private String[] patterns;

    @Setup
    public void setup() {
        patterns = new String[1000];
        for (int i = 0; i < 1000; i++) {
            patterns[i] = "   pattern" + i + "   ";
        }
    }

    @Benchmark
    public void originalImplementation() {
        String[] result = new String[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            result[i] = patterns[i].strip();
        }
    }

    @Benchmark
    public void streamImplementation() {
        String[] result = java.util.Arrays.stream(patterns)
                .map(String::strip)
                .toArray(String[]::new);
    }
	
//	 private static final byte[] ZIP_DATA;
//    
//    static {
//        try (InputStream inputStream = StringBenchmark.class.getResourceAsStream("/FileTEST.zip")) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024*1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//            ZIP_DATA = baos.toByteArray();
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read FileTEST.zip", e);
//        }
//    }
//
//    @Benchmark
//    public void decompressWithApacheCommons() throws IOException, ArchiveException {
//        try (InputStream inputStream = new ByteArrayInputStream(ZIP_DATA);
//             ArchiveInputStream<? extends ArchiveEntry> ais = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, inputStream)) {
//
//            ArchiveEntry entry;
//            while ((entry = ais.getNextEntry()) != null) {
//                byte[] buffer = new byte[1024*1024];
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int bytesRead;
//
//                while ((bytesRead = ais.read(buffer)) != -1) {
//                    baos.write(buffer, 0, bytesRead);
//                }
//            }
//        }
//    }
//
//    @Benchmark
//    public void decompressWithJavaZip() throws IOException {
//        try (InputStream inputStream = new ByteArrayInputStream(ZIP_DATA);
//             ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
//
//            ZipEntry entry;
//            while ((entry = zipInputStream.getNextEntry()) != null) {
//                byte[] buffer = new byte[1024*1024];
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                int bytesRead;
//
//                while ((bytesRead = zipInputStream.read(buffer)) != -1) {
//                    baos.write(buffer, 0, bytesRead);
//                }
//            }
//        }
//    }

}

