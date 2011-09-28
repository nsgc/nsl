package asia.nsgc.nsl;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.CategoryAxis;
import org.afree.chart.axis.CategoryLabelPositions;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.geom.RectShape;

import android.content.Context;
import android.graphics.Canvas;

import android.view.View;

public class ChartView extends View {
	
	private AFreeChart chart;

	public ChartView(Context context, DefaultCategoryDataset dataset, String title) {
		super(context);
		this.chart = createChart(dataset, title);
	}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectShape chartArea = new RectShape(0.0, 0.0, getWidth(), getHeight());
        this.chart.draw(canvas, chartArea);
    }	
    
    private AFreeChart createChart(DefaultCategoryDataset dataset, String title) {
    	AFreeChart chart = ChartFactory.createLineChart(title, "日時", "お金", dataset, PlotOrientation.VERTICAL, true, true, false);
    	
    	// X軸の項目名を斜めにするお仕事
    	CategoryPlot p = chart.getCategoryPlot();
    	CategoryAxis ca = p.getDomainAxis();
    	ca.setCategoryLabelPositions
    	(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
    	return chart;
    }
}
