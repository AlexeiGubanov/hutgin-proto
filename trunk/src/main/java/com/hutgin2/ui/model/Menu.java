package com.hutgin2.ui.model;

import java.util.List;

public class Menu {

    private Long id;
    private Menu parent;
    private List<Menu> childs;
    private String name;
    private Long type = 0l;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Menu> getChilds() {
        return childs;
    }

    public void addChild(Menu child) {
        this.childs.add(child);
        child.setParent(this);
    }

    public void removeChild(Menu child) {
        this.childs.remove(child);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
