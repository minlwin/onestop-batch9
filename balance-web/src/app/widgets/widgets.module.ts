import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroupComponent } from './form-group/form-group.component';
import { PageContentComponent } from './page-content/page-content.component';
import { PaginationComponent } from './pagination/pagination.component';
import { SummaryInfoComponent } from './summary-info/summary-info.component';

@NgModule({
  declarations: [
    FormGroupComponent,
    PageContentComponent,
    PaginationComponent,
    SummaryInfoComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FormGroupComponent,
    PageContentComponent,
    PaginationComponent,
    SummaryInfoComponent
  ]
})
export class WidgetsModule { }
