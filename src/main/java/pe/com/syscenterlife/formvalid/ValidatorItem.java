package pe.com.syscenterlife.formvalid;

/**
 * Clase Principal para validaciones de formularios
 *
 * @see <br>
 * Acepta las siguientes opciones:
 * <h1>Validación de Formularios Alternativas (Ejemplo:required|number|min:5|max:8):</h1>
 * <ol><li>required</li><li>number</li><li>min</li><li>max</li>
 * <li>username</li><li>email</li><li>phone</li>
 * <li>password</li><li>date</li><li>datef</li>
 * </ol>
 *
 * @see <a href = "https://github.com/davidmp" />Aqui Github</a>
 * 
 */
public class ValidatorItem {

    private final String rule;
    private final Object component;
    private final String name;

    public ValidatorItem(String arg_rule, Object arg_component, String arg_name) {
        this.rule = arg_rule;
        this.component = arg_component;
        this.name = arg_name;
    }

    public String getRule() {
        return this.rule;
    }

    public Object getField() {
        return this.component;
    }

    public String getName() {
        return this.name;
    }
}
