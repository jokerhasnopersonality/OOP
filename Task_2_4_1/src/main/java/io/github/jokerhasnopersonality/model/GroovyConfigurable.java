package io.github.jokerhasnopersonality.model;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.GroovyShell;
import groovy.lang.MetaProperty;
import groovy.util.DelegatingScript;
import io.github.jokerhasnopersonality.TaskInspector;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Collection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Basic DSL class for handling groovy objects.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GroovyConfigurable extends GroovyObjectSupport {
    private URI scriptPath;

    /**
     * Method is used when groovy encounters a
     * method call that is missing from the object.

     * @param name name of the method that was
     *             tried to be called
     * @param args list of failed method's arguments
     */
    @SneakyThrows
    public void methodMissing(String name, Object args) {
        MetaProperty metaProperty = getMetaClass().getMetaProperty(name);
        if (metaProperty != null) {
            Closure closure = (Closure) ((Object[]) args)[0];
            Object value = getProperty(name) == null
                    ? metaProperty.getType().getConstructor().newInstance() :
                    getProperty(name);
            if (value instanceof GroovyConfigurable) {
                ((GroovyConfigurable) value).scriptPath = scriptPath;
            }
            closure.setDelegate(value);
            closure.setResolveStrategy(Closure.DELEGATE_FIRST);
            closure.call();
            setProperty(name, value);
        } else {
            throw new IllegalArgumentException("No such field: " + name);
        }
    }

    /**
     * Method for import other objects like groups
     * or tasks from other groovy files.

     * @param path relative path to groovy file
     */
    @SneakyThrows
    public void include(String path) {
        URI uri = Paths.get(scriptPath).getParent().resolve(path).toUri();
        runFrom(uri);
        postProcess();
    }

    /**
     * Runs groovy script from the given URI.
     */
    @SneakyThrows
    public void runFrom(URI uri) {
        this.scriptPath = uri;
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());
        GroovyShell sh = new GroovyShell(TaskInspector.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript) sh.parse(uri);
        script.setDelegate(this);
        script.run();
    }

    /**
     * Method for post processing for identifying lists of objects.
     */
    @SneakyThrows
    public void postProcess() {
        for (MetaProperty metaProperty : getMetaClass().getProperties()) {
            Object value = getProperty(metaProperty.getName());
            if (Collection.class.isAssignableFrom(metaProperty.getType())
                    && value instanceof Collection) {
                ParameterizedType collectionType = (ParameterizedType) getClass()
                        .getDeclaredField(metaProperty.getName()).getGenericType();
                Class itemClass = (Class) collectionType.getActualTypeArguments()[0];
                if (GroovyConfigurable.class.isAssignableFrom(itemClass)) {
                    Collection collection = (Collection) value;
                    Collection newValue = collection.getClass()
                            .getConstructor().newInstance();
                    for (Object o : collection) {
                        if (o instanceof Closure) {
                            Object item = itemClass.getConstructor().newInstance();
                            ((GroovyConfigurable) item).setProperty("scriptPath", scriptPath);
                            ((Closure) o).setDelegate(item);
                            ((Closure) o).setResolveStrategy(Closure.DELEGATE_FIRST);
                            ((Closure) o).call();
                            ((GroovyConfigurable) item).postProcess();
                            newValue.add(item);
                        } else {
                            newValue.add(o);
                        }
                    }
                    setProperty(metaProperty.getName(), newValue);
                }
            }
        }
    }
}
