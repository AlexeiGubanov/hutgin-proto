package com.hutgin2.ui.model.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private Long id;
    private Menu parent;
    private List<Menu> childs = new ArrayList<>();
    private String name;
    private MenuType type = MenuType.NOT_DEFINED;
    private String path;

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

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
