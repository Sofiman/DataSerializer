package fr.sofianelecubeur.dataserializer;

import java.io.*;
import java.util.*;
import java.util.function.Function;

/**
 * Created by Sofiane on 14/10/2017.
 *
 * @author Sofiane
 */
public class CSVFileSerializer extends Serializer implements FileSerializer {

    private Map<Integer, Map<String, String>> values;
    private Set<String> columns;
    private String delemiter = ",";
    private ColumnType type;
    private int lines = 1;

    public CSVFileSerializer(UUID uuid) {
        super(CompilationType.CVS, uuid);
        this.values = new HashMap<>();
        this.columns = new HashSet<>();
        this.type = ColumnType.VERTICAL;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    /**
     * Set the value delmiter, in exel, the demlimiter is ","
     * @param delemiter the delimiter
     */
    public void setDelemiter(String delemiter) {
        if(delemiter == null) throw new NullPointerException();
        this.delemiter = delemiter;
    }

    /**
     * Add a column
     * @param column the column to add
     * @return if the column was added
     */
    public boolean addColumn(String column){
        if(column == null) throw new NullPointerException();
        if(columns.contains(column)) return false;
        columns.add(column);
        return true;
    }

    /**
     * Add multiples columns
     * @param columns alls columns to add
     */
    public void addColumns(String... columns){
        if(columns == null) throw new NullPointerException();
        for (String column : columns){
            if(!this.columns.contains(column)) this.columns.add(column);
        }
    }

    /**
     * Remove a column
     * @param column the target column
     * @return if the column was removed
     */
    public boolean removeColumn(String column){
        if(column == null) throw new NullPointerException();
        if(!columns.contains(column)) return false;
        columns.remove(column);
        return true;
    }

    /**
     * Get all columns
     * @return all columns
     */
    public Set<String> getColumns() {
        return columns;
    }

    @Override
    public void write(byte[] o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeInt(Integer o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeShort(Short o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeLong(Long o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeDouble(Double o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeFloat(Float o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeBoolean(Boolean o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeUTF(String o) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeObject(Object o) throws IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Set a value
     * @param column the target column
     * @param value the value
     */
    public void setValue(String column, String value){
        if(column == null || value == null) throw new NullPointerException();
        if(!this.columns.contains(column)) throw new RuntimeException("Unknown column '"+column+"'");
        Map<String, String> map = this.values.get(lines);
        if(map == null){
            map = new HashMap<>();
            this.values.put(lines, map);
        }
        map.put(column, format(value));
    }

    /**
     * Set a value
     * @param column the target column
     * @param value the value
     */
    public void setValue(String column, Object value) {
        this.setValue(column, value.toString());
    }

    /**
     *  Add a line (only when the columnType is Vertical)
     */
    public void addLine(){
        this.lines++;
    }

    /**
     * Total lines (used only when the columnType is Vertical)
     * @return alls lines
     */
    public int getLines() {
        return lines;
    }

    private static String format(String value) {
        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;
    }

    @Override
    public long compile(File file) throws IOException {
        long sT = System.currentTimeMillis();

        PrintWriter printWriter = new PrintWriter(file);
        String columnsLine = "";
        Map<String, Integer> indexMap = new HashMap<>();
        int index = 0;
        for (String column : columns){
            if(!columnsLine.isEmpty()) columnsLine += delemiter;
            columnsLine += column;
            indexMap.put(column, index++);
        }

        StringBuilder str = new StringBuilder();
        if(type == ColumnType.VERTICAL) {
            str.append(columnsLine);
            Map<Integer, String[]> lines = new HashMap<>();
            for (Map.Entry<Integer, Map<String, String>> line : this.values.entrySet()) {
                int l = line.getKey();
                lines.put(l, new String[columns.size()]);
                for (Map.Entry<String, String> entry : line.getValue().entrySet()) {
                    int id = indexMap.get(entry.getKey());
                    lines.get(l)[id] = entry.getValue();
                }
            }
            int lastLine = 0;
            for (Map.Entry<Integer, String[]> entry : lines.entrySet()) {
                if (lastLine != entry.getKey()) {
                    str.append("\n");
                    lastLine = entry.getKey();
                }
                String last = null;
                for (String value : entry.getValue()) {
                    str.append((last != null ? delemiter : "") + value);
                    last = value;
                }
            }
        }else if(type == ColumnType.HORIZONTAL){
            Map<String, List<String>> values = new HashMap<>();
            columns.forEach(col -> values.put(col, new ArrayList<>()));
            for(Map<String, String> map : this.values.values()){
                map.entrySet().forEach(val -> values.get(val.getKey()).add(val.getValue()));
            }
            boolean bool = false;
            StringJoiner joiner;
            for (Map.Entry<String, List<String>> entry : values.entrySet()){
                 joiner = new StringJoiner(delemiter);
                 for (String val : entry.getValue())
                    joiner.add(val);
                 str.append((bool ? "\n" : "")+entry.getKey()+delemiter+joiner.toString());
                 bool = true;
            }
        }
        printWriter.write(str.toString());
        printWriter.close();

        return (System.currentTimeMillis() - sT);
    }

    public enum ColumnType { VERTICAL, HORIZONTAL }

}
