package fr.sofianelecubeur.dataserializer;

import java.io.*;
import java.util.*;

/**
 * Created by Sofiane on 14/10/2017.
 *
 * @author Sofiane
 */
public class CSVFileSerializer extends Serializer implements FileSerializer {

    private String delemiter = ",";
    private Set<String> columns;
    private int lines = 1;
    private Map<Integer, Map<String, String>> values;

    public CSVFileSerializer(UUID uuid) {
        super(CompilationType.CVS, uuid);
        this.columns = new HashSet<>();
        this.values = new HashMap<>();
    }

    public void setDelemiter(String delemiter) {
        if(delemiter == null) throw new NullPointerException();
        this.delemiter = delemiter;
    }

    public boolean addColumn(String column){
        if(column == null) throw new NullPointerException();
        if(columns.contains(column)) return false;
        columns.add(column);
        return true;
    }

    public void addColumns(String... columns){
        if(columns == null) throw new NullPointerException();
        for (String column : columns){
            if(!this.columns.contains(column)) this.columns.add(column);
        }
    }

    public boolean removeColumn(String column){
        if(column == null) throw new NullPointerException();
        if(!columns.contains(column)) return false;
        columns.remove(column);
        return true;
    }

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

    public void setValue(String column, Object value) throws IllegalStateException {
        this.setValue(column, value.toString());
    }

    public void addLine(){
        this.lines++;
    }

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
        str.append(columnsLine);
        Map<Integer, String[]> lines = new HashMap<>();
        String[][] values = new String[this.values.size()][columns.size()];
        for (Map.Entry<Integer, Map<String, String>> line : this.values.entrySet()) {
            int l = line.getKey();
            lines.put(l, new String[columns.size()]);
            for (Map.Entry<String, String> entry : line.getValue().entrySet()) {
                int id = indexMap.get(entry.getKey());
                lines.get(l)[id] = entry.getValue();
            }
        }
        int lastLine = 0;
        for (Map.Entry<Integer, String[]> entry : lines.entrySet()){
           if(lastLine != entry.getKey()){
               str.append("\n");
               lastLine = entry.getKey();
           }
           String last = null;
           for(String value : entry.getValue()){
               str.append((last != null ? delemiter : "")+value);
               last = value;
           }
        }
        printWriter.write(str.toString());
        printWriter.close();

        return (System.currentTimeMillis() - sT);
    }
}
