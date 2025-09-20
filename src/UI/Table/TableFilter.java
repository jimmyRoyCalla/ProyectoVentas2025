package UI.Table;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

public class TableFilter extends RowFilter<TableModel, Integer> {
    private String[] keywords;
    
    public TableFilter(String[] keywords) {
        this.keywords = keywords;
    }
    
    @Override
    public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
        int count;
        
        for (String filter : keywords) {
            count = 0;

            for (int i = 0; i < entry.getValueCount(); i++) {
                if (entry.getStringValue(i).contains(filter)) count++;
            }

            if (count == 0) return false; 
        }

        return true;
    }
}
