import { Component, input } from '@angular/core';

@Component({
  selector: 'app-summary-info',
  templateUrl: './summary-info.component.html',
  styles: ``
})
export class SummaryInfoComponent {

  title = input.required<string>()
  value = input.required<number>()
}
