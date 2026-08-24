/**
		 * 
		 */
		/**
		 * 
		 */
		module JDKproj {
			requires javafx.controls;
		    requires javafx.fxml;

		    exports Calculator;
		    opens Calculator to javafx.fxml;
		}