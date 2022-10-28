package pe.com.syscenterlife.formvalid;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import pe.com.syscenterlife.hint.JHintTextField;


public class Validator {

    private Border defaultBorder = new JTextField().getBorder();
    private Object ErrorComponent;
    private List<String> errors = new ArrayList();
    private int MIN = 1, MAX = 2;
    private boolean fails = false;
    private String fieldName = "field_name", ruleValue = "value";
    private static Map<String, String> errorMessages = new HashMap<>();
    private static Border errorBorder = javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0), 2);

    public Validator(List<ValidatorItem> items) throws Exception {
        for (ValidatorItem item : items) {
            String ruleString = item.getRule(), field = item.getName();
            Object component = item.getField();
            
            String[] rules = splitRules(ruleString);

            for (String rule : rules) {
                String ruleStr = getRule(rule), value = getValue(component);
                boolean ruleError = false;
                String ruleVal = "0";
                switch (ruleStr) {
                    case "required":
                        ruleError = isNull(value);
                        break;
                    case "number":
                        ruleError = isntNumber(value);
                        break;
                    case "min":
                        String min = ruleVal = getRuleValueStr(rule);
                        ruleError = length(value, Integer.parseInt(min), MIN);
                        break;
                    case "max":
                        String max = ruleVal = getRuleValueStr(rule);
                        ruleError = length(value, Integer.parseInt(max), MAX);
                        break;
                    case "username":
                        String USERNAME_REGEX = "^[a-zA-Z0-9_]+$";
                        Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
                        ruleError = !USERNAME_PATTERN.matcher(value).matches();
                        break;
                    case "email":
                        String EMAIL_REGEX =
                        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*" +
                        "@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
                        ruleError = !EMAIL_PATTERN.matcher(value).matches();
                        break;
                    case "phone":
                        String PHONE_REGEX =
                                "^\\D?(\\d{3})\\D?\\D?(\\d{3})\\D?(\\d{3})$";
                        Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
                        ruleError = !PHONE_PATTERN.matcher(value).matches();
                        break;
                    case "password":
                        String PASSWORD_REGEX =
                            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,16}$";
                        Pattern PASSWORD_PATTERN =Pattern.compile(PASSWORD_REGEX);
                        ruleError = !PASSWORD_PATTERN.matcher(value).matches();
                        break;
                    case "date":                        
                        String DATE_REGEX =
                                "^(?=\\d{2}([-.,\\/])\\d{2}\\1\\d{4}$)(?:0[1-9]|1\\d|[2][0-8]|29(?!.02."
                                + "(?!(?!(?:[02468][1-35-79]|[13579][0-13-57-9])00)\\d{2}"
                                + "(?:[02468][048]|[13579][26])))|30(?!.02)|31"
                                + "(?=.(?:0[13578]|10|12))).(?:0[1-9]|1[012]).\\d{4}$";
                        Pattern DATE_PATTERN = Pattern.compile(DATE_REGEX);                                     
                        ruleError = !DATE_PATTERN.matcher(value).matches();
                        break;
                    case "datef":
                        String formatDate = ruleVal = getRuleValueStr(rule);
                        String DATE_REGEXF =
                                "^(?=\\d{2}([-.,\\/])\\d{2}\\1\\d{4}$)(?:0[1-9]|1\\d|[2][0-8]|29(?!.02."
                                + "(?!(?!(?:[02468][1-35-79]|[13579][0-13-57-9])00)\\d{2}"
                                + "(?:[02468][048]|[13579][26])))|30(?!.02)|31"
                                + "(?=.(?:0[13578]|10|12))).(?:0[1-9]|1[012]).\\d{4}$";
                        Pattern DATE_PATTERNF = Pattern.compile(DATE_REGEXF);
                        DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
                        if(!value.equals("")){
                                Date dv=formatter.parse(value);
                                DateFormat formatter1 = new SimpleDateFormat(formatDate);
                                value=value.equals("")?"":formatter1.format(dv);
                        }                        
                        ruleError = !DATE_PATTERNF.matcher(value).matches();
                        break;
                    default:
                        throw new Exception("Regla de validación : " + rule + " aún no es compatible.");
                }

                if (ruleError == true) {
                    fails = true;
                    errors.add(getMessage(ruleStr, field, ruleVal));
                }
                setBorder(component, ruleError, getMessage(ruleStr, field, ruleVal));
            }

        }
    }

    private String[] splitRules(String ruleString) {
        if (ruleString.contains("|")) {
            String[] solitted = ruleString.split("\\|");
            return solitted;
        }
        String[] defaultRule = {ruleString};
        return defaultRule;
    }

    private Border getErrorBorder() {
        return errorBorder;
    }

    public static void setErrorBorder(Border Border) {
        errorBorder = Border;
    }

    public Border getDefaultBorder() {
        return defaultBorder;
    }

    private boolean isTextComponent(Object component) {
        return component.getClass() == JTextField.class | component.getClass() == JTextArea.class;
    }

    private boolean isCombo(Object component) {
        return component.getClass() == JComboBox.class;
    }

    private boolean isPassField(Object component) {
        return component.getClass() == JPasswordField.class;
    }
    private boolean isHintTextComponent(Object component) {
        return component.getClass()==JHintTextField.class;
    }
    private boolean isJDateChooserComponent(Object component) {
        return component.getClass()==JDateChooser.class;
    }

    private boolean isNull(String value) {
        return (value == null || value.equals(""));
    }

    private boolean isntNumber(String value) {
        if (isNull(value)) {
            return false;
        }
        try {
            Double.parseDouble(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean length(String text, int lenght, int mode) {
        if (isNull(text)) {
            return false;
        }
        if (mode == MIN) {
            return text.length() < lenght;
        } else {
            return text.length() > lenght;
        }
    }

    public boolean isFails() {
        return fails;
    }
    
    public boolean isPasses(){
        return !fails;
    }

    private JTextField getTextField(Object component) {
        return (JTextField) component;
    }
    private JHintTextField getHintTextField(Object component) {
        return (JHintTextField) component;
    }
    private JDateChooser getJDateChooser(Object component) {
        return (JDateChooser) component;
    }

    private JComboBox getCombo(Object component) {
        return (JComboBox) component;
    }

    private JPasswordField getPwdField(Object component) {
        return (JPasswordField) component;
    }

    private String getRule(String rule) {
        return (rule.contains(":") ? rule.split(":")[0] : rule);
    }

    /*private int getRuleValue(String rule) throws Exception {
        if (isntNumber(rule) && rule.contains(":")) {
            return Integer.parseInt(rule.split(":")[1]);
        } else {
            throw new Exception("Regla del validador'" + rule + "' requiere un valor entero correcto para la validación. Ex: " + rule + ":5.");
        }
    }*/
    private String getRuleValueStr(String rule) throws Exception {
        if (isntNumber(rule) && rule.contains(":")) {
            return rule.split(":")[1];
        } else {
            throw new Exception("Regla del validador'" + rule + "' requiere un valor formato correcto. Ex: " + rule + ":5.");
        }
    }

    private void setBorder(Object component, boolean isError, String msgx) {
        if (ErrorComponent != component) {
            if (isTextComponent(component)) {
                getTextField(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
                getTextField(component).setToolTipText((isError) ? "<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3;'>"+msgx+"</div></html>" : "");
                UIManager.put("ToolTip.background", Color.ORANGE);
                UIManager.put("ToolTip.foreground", Color.BLACK);
                UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));                
            } else if (isCombo(component)) {
                getCombo(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
            } else if (isPassField(component)) {
                getPwdField(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
            }else if (isHintTextComponent(component)){
                getHintTextField(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
                getHintTextField(component).setToolTipText((isError) ? "<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3;'>"+msgx+"</div></html>" : "");
                UIManager.put("ToolTip.background", Color.ORANGE);
                UIManager.put("ToolTip.foreground", Color.BLACK);
                UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));              
            }else if(isJDateChooserComponent(component)){
                getJDateChooser(component).setBorder((isError) ? getErrorBorder() : getDefaultBorder());
                getJDateChooser(component).setToolTipText((isError) ? "<html><div style='margin:0 -3 0 -3; padding: 0 3 0 3;'>"+msgx+"</div></html>" : "");
                UIManager.put("ToolTip.background", Color.ORANGE);
                UIManager.put("ToolTip.foreground", Color.BLACK);
                UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));             
            }
        }
        if (isError) {
            ErrorComponent = component;
        }
    }

    private String getValue(Object component) throws Exception {
        String value = null;
        if (isTextComponent(component)) {
            value = getTextField(component).getText();
        } else if (isPassField(component)) {
            value = new String(getPwdField(component).getPassword());
        } else if (isCombo(component)) {
            value = getCombo(component).getSelectedItem()==null?"":getCombo(component).getSelectedItem().toString();
        }else if(isHintTextComponent(component)){
            value = getHintTextField(component).getHinText().equals(getHintTextField(component).getText())?"":getHintTextField(component).getText();
        } else if(isJDateChooserComponent(component)){   
            DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
            value = getJDateChooser(component).getDate()==null?"":formatter.format(getJDateChooser(component).getDate());
        } else {
            throw new Exception("Este componente no se pudo validar.");
        }
        return value;
    }

    private Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public static void setErrorMessages(Map<String, String> errorMessages) {
        Validator.errorMessages = errorMessages;
    }

    private Map<String, String> getDefaultMessages() {
        Map<String, String> map = new HashMap();
        map.put("required", "El "+fieldName+" es requerido.");
        map.put("min", "Minimo tamaño para "+fieldName+" es "+ruleValue+".");
        map.put("max", "Maximo tamaño para "+fieldName+" es "+ruleValue+".");
        map.put("number", "El texto debe ser un número válido para  "+fieldName+".");
        map.put("username", ""+fieldName+" no debe estar separado por espacios");//Otros
        map.put("phone", ""+fieldName+" acepta un numero con o sin guiones");
        map.put("email", ""+fieldName+" debe seguir un formato similar email@xx.xx ");
        map.put("password", ""+fieldName+" requiere de 8-16 caracteres con al menos un dígito, una letra minúscula, una letra mayúscula, un carácter especial sin espacios en blanco");
        map.put("date", ""+fieldName+" requiere en el formato dd-MM-yyyy considere que tiene  soporte para años bisiestos");        
        map.put("datef", ""+fieldName+" requiere en el formato dd-MM-yyyy o dd/MM/yyyy considere que tiene  soporte para años bisiestos");        
        return map;
    }

    private String getMessage(String rule, String field, String value) throws Exception {
        Map<String, String> msgs = getErrorMessages().isEmpty() ? getDefaultMessages() : getErrorMessages();
        if(msgs.get(rule) == null || msgs.get(rule).equals("")){
            throw new Exception("No hay mensaje definido para la regla de validación : "+rule+".");
        }
        return replaceShortcodes(msgs.get(rule), field, value);
    }

    private String replaceShortcodes(String pureMsg, String field, String value) {
        pureMsg = pureMsg.replaceAll(fieldName, field);
        pureMsg = pureMsg.replaceAll(ruleValue, value + "");
        return pureMsg;
    }
    


    public List getErrors() {
        return errors;
    }
}