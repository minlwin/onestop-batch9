import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroupComponent } from './form-group/form-group.component';
import { PageContentComponent } from './page-content/page-content.component';
import { PaginationComponent } from './pagination/pagination.component';

@NgModule({
  declarations: [
    FormGroupComponent,
    PageContentComponent,
    PaginationComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FormGroupComponent,
    PageContentComponent,
    PaginationComponent
  ]
})
export class WidgetsModule { }
