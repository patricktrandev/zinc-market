package cloud.tientn.zinc.utils;

import org.drools.compiler.builder.conf.DecisionTableConfigurationImpl;
import org.drools.drl.extensions.DecisionTableFactory;
import org.drools.io.FileSystemResource;
import org.kie.internal.builder.DecisionTableConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.Objects;

public class SpreadSheetToDrl {
    public static void main(String[] args) {
        generateDrl();
    }

    public static void generateDrl(){
        try{
            DecisionTableConfiguration decisionTableConfiguration = new DecisionTableConfigurationImpl();
            String genDrl="";
            FileSystemResource speadSheet = new FileSystemResource(getFile("rules/discountOrder/DiscountOrder.drl.xls"));
            genDrl= DecisionTableFactory.loadFromResource(speadSheet, decisionTableConfiguration);

            System.out.println(" ######################## RULES FROM SPREADSHEET ######################## ");
            System.out.println(genDrl);
            String outputPath = "src/main/resources/rules/discount/DiscountOrder.drl";
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(genDrl);
                System.out.println("Successfully wrote DRL to: " + outputPath);
            }
        } catch (Exception e) {
            System.err.println("Error generating DRL file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static File getFile(String fileName) {
        ClassLoader classLoader = SpreadSheetToDrl.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(Objects.requireNonNull(resource).getFile());
    }
}
