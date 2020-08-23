package web.mvc.hrm.model;

import java.util.Arrays;

public class DataTableResponse {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private Object[][] data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataTableResponse{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
