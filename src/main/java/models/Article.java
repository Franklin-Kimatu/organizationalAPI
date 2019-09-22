package models;

import java.util.Objects;

public class Article {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                Objects.equals(content, article.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, id);
    }

    private String content;
    private int id;

    public Article(String content){
        this.content =content;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
