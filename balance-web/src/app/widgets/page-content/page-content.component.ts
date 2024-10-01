import { Component, input } from '@angular/core';

@Component({
  selector: 'app-page-content',
  templateUrl: './page-content.component.html',
  styles: ``
})
export class PageContentComponent {

  title = input.required<string>()
  icon = input.required<string>()
}
