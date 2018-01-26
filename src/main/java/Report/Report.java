package Report;

public class Report
{
    private int total, processed, notProcessed, processedError;

    public Report()
    {
        total = 0;
        processed = 0;
        notProcessed = 0;
        processedError = 0;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        if(processed<0)
            throw new IllegalArgumentException();

        this.processed = processed;
    }

    public int getNotProcessed() {
        return notProcessed;
    }

    public void setNotProcessed(int notProcessed) {
        if(notProcessed<0)
            throw new IllegalArgumentException();

        this.notProcessed = notProcessed;
    }

    public int getProcessedError() {
        return processedError;
    }

    public void setProcessedError(int processedError) {
        if(processedError<0)
            throw new IllegalArgumentException();

        this.processedError = processedError;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Report{" +
                "total=" + total +
                ", processed=" + processed +
                ", notProcessed=" + notProcessed +
                ", processedError=" + processedError +
                '}';
    }
}
