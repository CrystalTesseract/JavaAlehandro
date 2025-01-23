package org.alexander.project.controllers.person.spec;

public class PersonSpecificationBuilder {
    private String name = null;
    private Integer age = null;
    private String email = null;
    private String inn = null;
    private String organizationdata = null;

    public PersonSpecificationBuilder withName(String name) {
        this.name = name;
        return this;
    }
    public PersonSpecificationBuilder withAge(Integer age) {
        this.age = age;
        return this;
    }
    public PersonSpecificationBuilder withEmail(String email) {
        this.email = email;
        return this;
    }
    public PersonSpecificationBuilder withInn(String inn) {
        this.inn = inn;
        return this;
    }
    public PersonSpecificationBuilder withOrganizationData(String organizationdata) {
        this.organizationdata = organizationdata;
        return this;
    }
    public PersonSpecification build() {
        return new PersonSpecification(name, age, email, inn, organizationdata);
    }
}
