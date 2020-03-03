package JFreeChartClasses;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
public class forDataSet {

    DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
    public forDataSet() {
	}
    static JFreeChart mChart = null;
    public void drawPic(String name,String X,String Y) {
    	mChart = ChartFactory.createLineChart(
    			name,//图名字
    			X,//横坐标
    			Y,//纵坐标
            	this.mDataset,//数据集
            	PlotOrientation.VERTICAL,
            	true, // 显示图例
            	true, // 采用标准生成器
            	false);// 是否生成超链接
    	
    	 CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
         mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
         mPlot.setRangeGridlinePaint(Color.BLUE);//背景底部横虚线
         mPlot.setOutlinePaint(Color.RED);//边界线
  
         CategoryAxis Axis = mPlot.getDomainAxis();
         Axis.setLabelFont(new Font("SansSerif",Font.TRUETYPE_FONT,12));
         Axis.setTickLabelFont(new Font("SansSerif",Font.TRUETYPE_FONT,12));
         Axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
         
         ChartFrame mChartFrame = new ChartFrame("折线图", mChart);
         mChartFrame.pack();
         mChartFrame.setVisible(true);
    }
 
    public  void GetDataset(int value,String name,String date)
    {
        this.mDataset.addValue(value, name, date);
    }
}