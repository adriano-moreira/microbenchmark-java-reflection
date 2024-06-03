package study.perf.reflec.myframework;

public class RequiredFieldException extends RuntimeException {
    private final String fieldName;

    public RequiredFieldException(String fieldName) {
        super("required field '" + fieldName + "' is missing");
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
