import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroupComponent } from './form-group/form-group.component';
import { PageContentComponent } from './page-content/page-content.component';

@NgModule({
  declarations: [
    FormGroupComponent,
    PageContentComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    FormGroupComponent,
    PageContentComponent
  ]
})
export class WidgetsModule { }
