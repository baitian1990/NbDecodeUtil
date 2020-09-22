package nb.nbpro;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BaseEntity {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Class<? extends BaseEntity> aClass = this.getClass();
        sb.append(aClass.getSimpleName());
        sb.append("{");
        Field[] declaredFields = aClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            boolean aStatic = Modifier.isStatic(field.getModifiers());
            if (!aStatic){
                field.setAccessible(true);
                sb.append(field.getName());
                sb.append("=");
                try {
                    sb.append(field.get(this).toString());
                    if (i == declaredFields.length -1){
                        while (sb.toString().endsWith(","))
                            sb.deleteCharAt(sb.length()-1);
                        sb.append("}");
                    }else {
                        sb.append(",");
                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

        return sb.toString();
    }
}
