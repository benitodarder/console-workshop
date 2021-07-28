package local.tin.tests.url.downloader.model;

import java.util.Objects;
import org.jsoup.nodes.Document;

/**
 *
 * @author benitodarder
 */
public class JSoupConf {
    
    private String urlString;
    private Document document;
    private String localFilesPath;

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document doc) {
        this.document = doc;
    }

    public String getLocalFilesPath() {
        return localFilesPath;
    }

    public void setLocalFilesPath(String localFilesPath) {
        this.localFilesPath = localFilesPath;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.urlString);
        hash = 17 * hash + Objects.hashCode(this.document);
        hash = 17 * hash + Objects.hashCode(this.localFilesPath);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JSoupConf other = (JSoupConf) obj;
        if (!Objects.equals(this.urlString, other.urlString)) {
            return false;
        }
        if (!Objects.equals(this.localFilesPath, other.localFilesPath)) {
            return false;
        }
        return Objects.equals(this.document, other.document);
    }
    
    
    
}
