import { AfterViewInit, Component, signal } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { CommonModule } from '@angular/common';

import * as am5 from "@amcharts/amcharts5/index";
import * as am5xy from "@amcharts/amcharts5/xy";
import am5themes_Animated from "@amcharts/amcharts5/themes/Animated";
import { SummaryService } from '../../../services/api/summary.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [WidgetsModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrls: [
    './home.component.scss'
  ]
})
export class HomeComponent implements AfterViewInit{

  root:any
  summary = signal<any>(undefined)

  constructor(private service:SummaryService) {
  }

  ngAfterViewInit(): void {
    this.root = am5.Root.new("chartdiv");
    this.service.loadData().subscribe(result => {
      const {chartData, ... summaryData} = result
      this.summary.set(summaryData)
      this.loadChart(chartData)
    })
  }

  private loadChart(data:any[]): void {

    if(this.root) {
      this.root.setThemes([
        am5themes_Animated.new(this.root)
      ]);

      this.root.container.children.clear()

      let chart = this.root.container.children.push(am5xy.XYChart.new(this.root, {
        panX: false,
        panY: false,
        paddingLeft: 0,
        wheelX: "panX",
        wheelY: "zoomX",
        layout: this.root.verticalLayout
      }));


      let legend = chart.children.push(
        am5.Legend.new(this.root, {
          centerX: am5.p50,
          x: am5.p50
        })
      );

      let xRenderer = am5xy.AxisRendererX.new(this.root, {
        cellStartLocation: 0.1,
        cellEndLocation: 0.9,
        minorGridEnabled: true
      })

      let xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(this.root, {
        categoryField: "date",
        renderer: xRenderer,
        tooltip: am5.Tooltip.new(this.root, {})
      }));

      xRenderer.grid.template.setAll({
        location: 1
      })

      xAxis.data.setAll(data);

      let yAxis = chart.yAxes.push(am5xy.ValueAxis.new(this.root, {
        renderer: am5xy.AxisRendererY.new(this.root, {
          strokeOpacity: 0.1
        })
      }));

      function makeSeries(root:any, fieldName:string, valueName:string) {
        let series = chart.series.push(am5xy.ColumnSeries.new(root, {
          name: fieldName,
          xAxis: xAxis,
          yAxis: yAxis,
          valueYField: valueName,
          categoryXField: "date"
        }));

        series.columns.template.setAll({
          tooltipText: "{name}, {categoryX}:{valueY}",
          width: am5.percent(90),
          tooltipY: 0,
          strokeOpacity: 0
        });

        series.data.setAll(data);

        series.appear();

        series.bullets.push(function () {
          return am5.Bullet.new(root, {
            locationY: 0,
            sprite: am5.Label.new(root, {
              text: "{valueY}",
              fill: root.interfaceColors.get("alternativeText"),
              centerY: 0,
              centerX: am5.p50,
              populateText: true
            })
          });
        });

        legend.data.push(series);
      }

      makeSeries(this.root, "Debit", "debit");
      makeSeries(this.root, "Credit", "credit");

      chart.appear(1000, 100);
    }
  }
}
