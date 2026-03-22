package com.ritesh.proto_buf;

public class Department {

    private int id;
    private String name;
    public Department() {}



    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public String getName()          { return name; }
    public void setName(String name) { this.name = name; }



    // ─── Object Overrides ─────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "}";
    }
}