package gr.uoa.di.madgik.lcapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection ="licensesCompatibilityMatrix")
public class License {

    @Id
    private String id;
    private String name;
    @JsonIgnore
    private List<String> compatibleLicenses;
    private String type;
    @JsonIgnore
    private List<String> permissions;
    @JsonIgnore
    private List<String> prohibitions;
    @JsonIgnore
    private List<String> obligations;
    private String url;

    public License() {
    }

    public License(String id, String name, List<String> compatibleLicenses, String type, List<String> permissions, List<String> prohibitions, List<String> obligations, String url) {
        this.id = id;
        this.name = name;
        this.compatibleLicenses = compatibleLicenses;
        this.type = type;
        this.permissions = permissions;
        this.prohibitions = prohibitions;
        this.obligations = obligations;
        this.url = url;
    }

    @Override
    public String toString() {
        return "License{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", compatibleLicenses=" + compatibleLicenses +
                ", type='" + type + '\'' +
                ", permissions=" + permissions +
                ", prohibitions=" + prohibitions +
                ", obligations=" + obligations +
                ", url='" + url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCompatibleLicenses() {
        return compatibleLicenses;
    }

    public void setCompatibleLicenses(List<String> compatibleLicenses) {
        this.compatibleLicenses = compatibleLicenses;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getProhibitions() {
        return prohibitions;
    }

    public void setProhibitions(List<String> prohibitions) {
        this.prohibitions = prohibitions;
    }

    public List<String> getObligations() {
        return obligations;
    }

    public void setObligations(List<String> obligations) {
        this.obligations = obligations;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


