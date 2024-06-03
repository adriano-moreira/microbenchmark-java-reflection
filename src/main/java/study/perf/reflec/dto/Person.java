package study.perf.reflec.dto;

import study.perf.reflec.myframework.RequiredField;

import java.util.Date;

public class Person {

    @RequiredField
    private Long cod;

    @RequiredField
    private String name;
    private String email;
    private Date createdAt;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @RequiredField
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
