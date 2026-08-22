/**
 * 
 */
/**
 * 
 */
module HWs {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	
	
	opens HW3 to javafx.graphics;
	exports HW3;
}