package Feed;

public class Media extends Feed
{
    private String codeMedia, isDefault;

    public Media()
    {
        this.codeMedia = null;
        this.isDefault = null;
    }

    public String getCodeMedia() {
        return codeMedia;
    }

    public void setCodeMedia(String codeMedia) {
        this.codeMedia = codeMedia;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "Media{" +
                ", codeMedia='" + codeMedia + '\'' +
                ", isDefault='" + isDefault + '\'' +
                '}';
    }
}