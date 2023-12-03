package com.mirea.diplom.factory;

import com.sun.codemodel.JCodeModel;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.mirea.diplom.utils.Constants.JSON_MODEL_PACKAGE;
import static com.mirea.diplom.utils.Constants.OUTPUT_JAVA_CLASS_DIRECTORY;

@UtilityClass
public class JsonSchemaToPojoFactory {
    public static void convertJsonToJavaClass(String pathToJsonScheme, String javaClassName)
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        File file = new File(pathToJsonScheme);
        File outputJavaClassDirectory = new File(OUTPUT_JAVA_CLASS_DIRECTORY);
        URL inputJsonUrl = file.toURI().toURL();
        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, JSON_MODEL_PACKAGE, inputJsonUrl);

        jcodeModel.build(outputJavaClassDirectory);
    }
}
