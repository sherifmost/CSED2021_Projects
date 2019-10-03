package eg.edu.alexu.csd.oop.jdbc.view;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableController {
	private int colsNum;
	private Object[][] data;
	private String[] colsName;

	public TableController(int colsNum, Object[][] data, String[] colsName) {
		this.colsNum = colsNum;
		setData(data);
		setCols(colsName);
	}

	private void setData(Object[][] data) {
		this.data = new Object[data.length][data[0].length];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	private void setCols(String[] colsName) {
		this.colsName = new String[colsName.length];
		for (int i = 0; i < colsName.length; i++) {
			this.colsName[i] = colsName[i];
		}
	}

	@FXML
	private TableView<Object[]> tbl = new TableView<>();

	private void fillList() {

		tbl.getColumns().clear();
		tbl.getItems().clear();
		
		int numRows = data.length;
		if (numRows == 0)
			return;

		int numCols = colsNum;

		for (int i = 0; i < numCols; i++) {
			TableColumn<Object[], Object> column = new TableColumn<>(colsName[i]);
			final int columnIndex = i;
			column.setCellValueFactory(cellData -> {
				Object[] row = cellData.getValue();
				return new SimpleObjectProperty<Object>(row[columnIndex]);
			});
			column.setPrefWidth(tbl.getPrefWidth()/numCols);
			tbl.getColumns().add(column);
		}
		
		for (int i = 0; i < numRows; i++) {
			tbl.getItems().add(data[i]);
		}
	}

	@FXML
	private void initialize() {
		fillList();
	}
}
