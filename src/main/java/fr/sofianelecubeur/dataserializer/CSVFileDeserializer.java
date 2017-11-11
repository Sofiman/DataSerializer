package fr.sofianelecubeur.dataserializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Sofiane on 17/10/2017.
 *
 * @author Sofiane
 */
public class CSVFileDeserializer extends Deserializer {

    private File file;
    private String delemiter = ",";
    private CSVFileSerializer.ColumnType columnType;
    private Set<String> columns;
    private int rows;
    private Map<Integer, Map<String, String>> values;

    public CSVFileDeserializer(File file, UUID identifier) throws IOException {
        this(new FileInputStream(file), identifier);
        this.file = file;
        this.columnType = CSVFileSerializer.ColumnType.VERTICAL;
        this.loadValues(file);
    }

    public CSVFileDeserializer(File file, String delimiter, UUID identifier) throws IOException {
        this(file, CSVFileSerializer.ColumnType.VERTICAL, delimiter, identifier);
    }

    public CSVFileDeserializer(File file, CSVFileSerializer.ColumnType columnType, String delimiter, UUID identifier) throws IOException {
        this(new FileInputStream(file), identifier);
        this.file = file;
        this.columnType = columnType;
        this.delemiter = delimiter;
        this.loadValues(file);
    }

    private CSVFileDeserializer(FileInputStream in, UUID identifier) throws IOException {
        super(CompilationType.CVS, in, identifier);
    }

    protected void loadValues(File file) throws IOException {
        this.columns = new HashSet<>();
        this.values = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(file.toURI()));
        if(lines.isEmpty()) throw new IllegalStateException("Invalid CSV file.");
        if(columnType == CSVFileSerializer.ColumnType.VERTICAL) {
            String line = lines.stream().findFirst().get();
            for (String col : line.split(delemiter))
                columns.add(col);

            Map<Integer, String> indexMap = new HashMap<>();
            int index = 0;
            for (String column : columns)
                indexMap.put(index++, column);
            rows = (lines.size() - 1);

            Map<String, String> values;
            int cur;
            for (int i = 1; i < (rows + 1); i++) {
                line = lines.get(i);
                values = new HashMap<>();
                cur = 0;
                for (String val : line.split(delemiter)) {
                    values.put(indexMap.get(cur++), val);
                }
                this.values.put((i - 1), values);
            }
        }else {
            int cur = 0;
            for (String line : lines){
                String[] content = line.split(delemiter);
                String column = content[0];
                columns.add(column);
                for (int i = 1; i < content.length; i++) {
                    values.put(cur++, Collections.singletonMap(column, content[i]));
                }
            }
            rows = cur;
        }
    }

    public Set<String> getColumns() {
        return columns;
    }

    public String getColumnsString() {
        StringJoiner joiner = new StringJoiner(", ");
        columns.forEach(col -> joiner.add(col));
        return joiner.toString();
    }

    /**
     * Get the delimiter
     * @return the delimiter
     */
    public String getDelemiter() {
        return delemiter;
    }

    /**
     * Get total rows
     * @return total rows
     */
    public int getRows() {
        return rows;
    }

    public String getValue(int line, String column) {
        return values.get(line).get(column);
    }

    public Collection<String> getValues(String column) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            Map<String, String> map = values.get(i);
            if(map.containsKey(column))
               for (Map.Entry<String, String> entry : map.entrySet()){
                   if(entry.getKey().equalsIgnoreCase(column))
                       list.add(entry.getValue());
               }
        }
        return list;
    }

    /**
     * Get all values for a line
     * @param line the target line
     * @return alls values for the line
     */
    public Collection<String> getValues(int line) {
        return values.get(line).values();
    }

    /**
     * Get all values in a string
     * @return All values formated
     */
    public String getValuesString(){
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < rows; i++)
            getValues(i).forEach(val -> joiner.add(val));
        return joiner.toString();
    }
}
