package vn.com.viettel.gencode.gen;

import org.apache.log4j.Logger;
import vn.com.viettel.gencode.entities.MethodEntity;
import vn.com.viettel.gencode.entities.ObjectEntity;
import vn.com.viettel.gencode.entities.VariableEntity;
import vn.com.viettel.gencode.utils.FunctionCommon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import static vn.com.viettel.gencode.gen.GenEntity.getListPrivateKey;

/**
 * Gen class ServiceJPA
 */
public class GenServiceJPA {

    private static final Logger LOGGER = Logger.getLogger(GenServiceJPA.class);

    public static void writeClassServiceJPA(ObjectEntity itemObject) {

        //thuc hien gen class
        if (itemObject != null) {
            //thuc hien kiem tra trong cau sql lay ra danh sach bang
            List<String> listTableName = FunctionCommon.getListTableFromSql(itemObject);
            if (listTableName == null || listTableName.size() == 0) {
                return;
            }
            for (String stringTableName : listTableName) {
                List<String> listPr = getListPrivateKey(stringTableName);
                if (listPr == null || listPr.size() == 0) {
                    continue;
                }
                writeClassServiceTable(stringTableName, itemObject);
            }

        }
    }

    private static void writeClassServiceTable(String stringTableName, ObjectEntity itemObject) {
        try {
            String classTableService = Character.toUpperCase(stringTableName.charAt(0)) + FunctionCommon.camelcasify(stringTableName.substring(1));
            String strClassServiceJPA = classTableService + "ServiceJPA";
            String pathFileServiceJPA = FunctionCommon.getPropertiesValue("src.url.create.code")
                    + File.separator + "src"
                    + File.separator + "main"
                    + File.separator + "java"
                    + File.separator + "vn"
                    + File.separator + "com"
                    + File.separator + "viettel"
                    + File.separator + "services"
                    + File.separator + "jpa"
                    + File.separator + strClassServiceJPA + ".java";
            File file = new File(pathFileServiceJPA);
            if (file.exists()) {
                return;
            } else {
                file.getParentFile().mkdirs();
                FileWriter fileWriterAction = new FileWriter(pathFileServiceJPA);
                try (PrintWriter printWriteAction = new PrintWriter(fileWriterAction)) {
                    StringBuilder strContentCodeAction = generateClassServiceJPA(stringTableName, itemObject);
                    printWriteAction.print(strContentCodeAction);
                }
            }
            System.out.println("Generate File ServiceJPA = " + strClassServiceJPA);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            LOGGER.error(e);
        }
    }

    private static StringBuilder generateClassServiceJPA(String stringTableName, ObjectEntity itemObject) {
        stringTableName = Character.toUpperCase(stringTableName.charAt(0)) + stringTableName.substring(1);
        String strClassService = FunctionCommon.camelcasify(stringTableName) + "ServiceJPA";
        String strClassEntity = FunctionCommon.camelcasify(stringTableName) + "Entity";
        String strClassRepository = FunctionCommon.camelcasify(stringTableName) + "RepositoryJPA";
        String strClassDTO = itemObject.getClassName() + "DTO";
        String varTableNameCamel = Character.toLowerCase(strClassEntity.charAt(0)) + FunctionCommon.camelcasify(strClassEntity.substring(1));
        String strDescClass = "Create Service For Table Name " + stringTableName;
        StringBuilder strContentCodeAction = new StringBuilder();

        // File ServiceJPA
        //==============chen header import======================================
        strContentCodeAction.append("package vn.com.viettel.services.jpa;").append("\r\r");
        strContentCodeAction.append("import vn.com.viettel.core.dto.response.BaseResultSelect;\r");
        strContentCodeAction.append("import vn.com.viettel.dto.").append(strClassDTO).append(";\r");
        strContentCodeAction.append("import vn.com.viettel.entities.").append(strClassEntity).append(";\r");
        strContentCodeAction.append("import vn.com.viettel.repositories.jpa.").append(strClassRepository).append(";\r");
        strContentCodeAction.append("import java.util.List;").append("\r");
        strContentCodeAction.append("import java.util.Optional;").append("\r");
        strContentCodeAction.append("import lombok.AllArgsConstructor;").append("\r");
        strContentCodeAction.append("import org.springframework.stereotype.Service;").append("\r");
        strContentCodeAction.append("import org.springframework.data.domain.Page;").append("\r");
        strContentCodeAction.append("import org.springframework.data.domain.PageRequest;").append("\r");
        strContentCodeAction.append("import org.springframework.data.domain.Pageable;").append("\r");

        //thuc hien gen comment
        strContentCodeAction.append("/**").append("\r");
        strContentCodeAction.append(" * ").append("Autogen class: ").append(strDescClass).append("\r");
        strContentCodeAction.append(" * ").append("\r");
        strContentCodeAction.append(" * @author ToolGen").append("\r");
        strContentCodeAction.append(" * @date ").append(new Date()).append("\r");
        strContentCodeAction.append(" */").append("\r");

        //thuc hien code phan content class
        strContentCodeAction.append("@Service").append("\r");
        strContentCodeAction.append("@AllArgsConstructor").append("\r");
        strContentCodeAction.append("public class ").append(strClassService).append(" {").append("\r\r");

//        strContentCodeAction.append("    @Autowired").append("\r");
        String varStringTableNameCamel = Character.toLowerCase(stringTableName.charAt(0)) + FunctionCommon.camelcasify(stringTableName.substring(1));
        strContentCodeAction.append("    private final ").append(strClassRepository).append(" ").append(varStringTableNameCamel).append(";\r\r");

        //method get all
        strContentCodeAction.append("    public List<").append(strClassEntity).append("> findAll() {\r");
        strContentCodeAction.append("        return ").append(varStringTableNameCamel).append(".findAll();").append("\r");
        strContentCodeAction.append("    }").append("\r\r");

        //method  add
        strContentCodeAction.append("    public ").append(strClassEntity).append(" save(").append(strClassEntity).append(" ").append(varTableNameCamel).append(") {\r");
        strContentCodeAction.append("        return ").append(varStringTableNameCamel).append(".save(").append(varTableNameCamel).append(");\r");
        strContentCodeAction.append("    }").append("\r\r");

        //method  get by Id
        strContentCodeAction.append("    public Optional<").append(strClassEntity).append("> findById(Long id) {").append("\r");
        strContentCodeAction.append("        return ").append(varStringTableNameCamel).append(".findById(id);").append("\r");
        strContentCodeAction.append("    }").append("\r\r");

        //method  delete
        strContentCodeAction.append("    public void").append(" deleteById(Long id) {").append("\r");
        strContentCodeAction.append("        ").append(varStringTableNameCamel).append(".deleteById(id);").append("\r");
        strContentCodeAction.append("    }").append("\r\r");

        //method  get one
        strContentCodeAction.append("    public ").append(strClassEntity).append(" getReferenceById(Long id) {").append("\r");
        strContentCodeAction.append("        return ").append(varStringTableNameCamel).append(".getReferenceById(id);").append("\r");
        strContentCodeAction.append("    }").append("\r\r");

        //method  get one
        strContentCodeAction.append("    public Boolean existsById(Long id) {").append("\r");
        strContentCodeAction.append("        return ").append(varStringTableNameCamel).append(".existsById(id);").append("\r");
        strContentCodeAction.append("    }").append("\r\r");

        // Gen method Service JPA
        List<VariableEntity> variableEntities = GenDTO.getListVariableFrom(itemObject, true);
        itemObject.getListMethod().forEach((method) -> {
            if (method.getJpa() != null && method.getJpa()) {
                strContentCodeAction.append(generateServiceJPA(variableEntities, method, strClassDTO, strClassEntity, varStringTableNameCamel));
            }
        });

        strContentCodeAction.append("\n}");
        return strContentCodeAction;
    }


    /**
     * Gen service JPA
     * @param variableEntities
     * @param method
     * @param strClassDTO
     * @param strClassEntity
     * @param strTableNameCamel
     * @return
     */
    private static StringBuilder generateServiceJPA(List<VariableEntity> variableEntities, MethodEntity method, String strClassDTO, String strClassEntity, String strTableNameCamel) {
        StringBuilder strContentCodeAction = new StringBuilder();
        StringBuilder strParamsMethod = new StringBuilder();
        if (method.getValue() != null && method.getValue().trim().length() > 0) {
            List<String> listParams = FunctionCommon.getListParamsFromUrl(method.getValue());
            boolean first = true;
            for (String itemParams : listParams) {
                if (itemParams != null && itemParams.trim().length() > 0) {
                    if (!first) {
                        strParamsMethod.append(",");
                    }
                    if (itemParams.toLowerCase().endsWith("id")) {
                        strParamsMethod.append(" Long ").append(itemParams);
                    } else {
                        strParamsMethod.append(" String ").append(itemParams);
                    }
                    first = false;
                }
            }
        }
        String varClassDTO = Character.toLowerCase(strClassDTO.charAt(0)) + FunctionCommon.camelcasify(strClassDTO.substring(1));
        strContentCodeAction.append("    public Object ").append(method.getName()).append("(").append(strClassDTO).append(" ").append(varClassDTO);
//        if (method.getParams() != null) {
//            method.getParams().forEach((param) -> {
//                VariableEntity variableEntity = variableEntities.stream().filter(variable -> param.equalsIgnoreCase(variable.getColumnName())).findAny().orElse(null);
//                if (variableEntity != null) {
//                    strContentCodeAction.append(", ").append(variableEntity.getTypeVariable()).append(" ").append(variableEntity.getColumnName());
//                } else {
//                    strContentCodeAction.append(", ").append("String ").append(param);
//                }
//            });
//        }
        if (strParamsMethod.toString().trim().length() > 0) {
            strContentCodeAction.append(", ").append(strParamsMethod);
        }
        strContentCodeAction.append(") {").append("\r");
        strContentCodeAction.append("        Pageable pageable;\r");
        strContentCodeAction.append("        if (").append(varClassDTO).append(".getStartRecord() != null && ").append(varClassDTO).append(".getPageSize() != null) {\r");
        strContentCodeAction.append("           pageable = PageRequest.of(").append(varClassDTO).append(".getStartRecord() / ")
                .append(varClassDTO).append(".getPageSize(), ").append(varClassDTO).append(".getPageSize());\r");
        strContentCodeAction.append("        } else {\r");
        strContentCodeAction.append("           pageable = PageRequest.of (0, 10);\r");
        strContentCodeAction.append("        }\r");

//        StringBuilder strParams = new StringBuilder();
//        if (method.getParams() != null) {
//            method.getParams().forEach((param) -> {
//                VariableEntity variableEntity = variableEntities.stream().filter(variable -> param.equalsIgnoreCase(variable.getColumnName())).findAny().orElse(null);
//                if (variableEntity != null) {
//                    strParams.append(variableEntity.getColumnName()).append(", ");
//                } else {
//                    strParams.append(param).append(", ");
//                }
//            });
//        }
        strContentCodeAction.append("        Page<").append(strClassEntity).append("> page = ").append(strTableNameCamel).append(".").append(method.getName())
                .append("(").append(varClassDTO).append(", pageable);").append("\r");
        if (method.getCount() != null && method.getCount() == 1) {
            strContentCodeAction.append("        return new BaseResultSelect(page.getContent(), page.getTotalElements());\r");
        } else {
            strContentCodeAction.append("        return page.getContent();\r");
        }
        strContentCodeAction.append("    }").append("\r\r");
        return strContentCodeAction;
    }
}
