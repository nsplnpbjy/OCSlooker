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
    			name,//ͼ����
    			X,//������
    			Y,//������
            	this.mDataset,//���ݼ�
            	PlotOrientation.VERTICAL,
            	true, // ��ʾͼ��
            	true, // ���ñ�׼������
            	false);// �Ƿ����ɳ�����
    	
    	 CategoryPlot mPlot = (CategoryPlot)mChart.getPlot();
         mPlot.setBackgroundPaint(Color.LIGHT_GRAY);
         mPlot.setRangeGridlinePaint(Color.BLUE);//�����ײ�������
         mPlot.setOutlinePaint(Color.RED);//�߽���
  
         CategoryAxis Axis = mPlot.getDomainAxis();
         Axis.setLabelFont(new Font("SansSerif",Font.TRUETYPE_FONT,12));
         Axis.setTickLabelFont(new Font("SansSerif",Font.TRUETYPE_FONT,12));
         Axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
         
         ChartFrame mChartFrame = new ChartFrame("����ͼ", mChart);
         mChartFrame.pack();
         mChartFrame.setVisible(true);
    }
 
    public  void GetDataset(int value,String name,String date)
    {
        this.mDataset.addValue(value, name, date);
    }
}