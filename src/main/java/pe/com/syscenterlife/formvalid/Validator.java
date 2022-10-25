package pe.com.syscenterlife.formvalid;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
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

/**
 * Clase Principal para validaciones de formularios
 *
 * @see <br>
 * Acepta las siguientes opciones:
 * <h1>Validación de Formularios Alternativas (Ejemplo:required|number|min:5|max:8):</h1>
 * <ol><li>required</li><li>number</li><li>min</li><li>max</li>
 * <li>username</li><li>email</li><li>phone</li>
 * <li>password</li><li>date</li>
 * </ol>
 *
 * @see <a href = "https://github.com/davidmp" />Aqui Github</a>
 * 
 */
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
                int ruleVal = 0;
                switch (ruleStr) {
                    case "required":
                        ruleError = isNull(value);
                        break;
                    case "number":
                        ruleError = isntNumber(value);
                        break;
                    case "min":
                        int min = ruleVal = getRuleValue(rule);
                        ruleError = length(value, min, MIN);
                        break;
                    case "max":
                        int max = ruleVal = getRuleValue(rule);
                        ruleError = length(value, max, MAX);
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

    private JComboBox getCombo(Object component) {
        return (JComboBox) component;
    }

    private JPasswordField getPwdField(Object component) {
        return (JPasswordField) component;
    }

    private String getRule(String rule) {
        return (rule.contains(":") ? rule.split(":")[0] : rule);
    }

    private int getRuleValue(String rule) throws Exception {
        if (isntNumber(rule) && rule.contains(":")) {
            return Integer.parseInt(rule.split(":")[1]);
        } else {
            throw new Exception("Regla del validador'" + rule + "' requiere un valor entero correcto para la validación. Ex: " + rule + ":5.");
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
        map.put("date", ""+fieldName+" requiere en el formato dd-mm-yyyy considere que tiene  soporte para años bisiestos");        
        return map;
    }

    private String getMessage(String rule, String field, int value) throws Exception {
        Map<String, String> msgs = getErrorMessages().isEmpty() ? getDefaultMessages() : getErrorMessages();
        if(msgs.get(rule) == null || msgs.get(rule).equals("")){
            throw new Exception("No hay mensaje definido para la regla de validación : "+rule+".");
        }
        return replaceShortcodes(msgs.get(rule), field, value);
    }

    private String replaceShortcodes(String pureMsg, String field, int value) {
        pureMsg = pureMsg.replaceAll(fieldName, field);
        pureMsg = pureMsg.replaceAll(ruleValue, value + "");
        return pureMsg;
    }

    public List getErrors() {
        return errors;
    }
}
