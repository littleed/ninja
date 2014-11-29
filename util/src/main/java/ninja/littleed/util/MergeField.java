package ninja.littleed.util;

public enum MergeField {

	STUDENT_FORENAME("Student Forename", "The forename of the student", "${student.forename}", "$\\{student\\.forename\\}"),
	STUDENT_SURNAME("Student Surname", "The surname of the student", "${student.surname}", "$\\{student\\.surname\\}"),
	MAIN_CONTACT_POSTCODE("Main Contact's Postcode", "The postcode of the main contact", "${contact.postcode}", "$\\{contact\\.postcode\\}");
	
	private String displayName;
	private String description;
	private String fieldName;
	private String escapedFieldName;
	
	private MergeField(String displayName, String description,
			String fieldName, String escapedFieldName) {
		this.displayName = displayName;
		this.description = description;
		this.fieldName = fieldName;
		this.escapedFieldName = escapedFieldName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getDescription() {
		return description;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getEscapedFieldName() {
		return escapedFieldName;
	}
	
	public String toString(){
		return displayName;
	}
	
}
