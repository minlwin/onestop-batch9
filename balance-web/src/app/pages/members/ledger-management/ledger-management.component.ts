import { Component, computed, signal, viewChild } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { LedgerEditComponent } from './ledger-edit/ledger-edit.component';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LedgerManagementService } from '../../../services/api/ledger-management.service';
import { PageInfo } from '../../../services/commons';
import { RouterLink } from '@angular/router';
import { PagerComponent } from '../../../widgets/pagination/pagination.component';

@Component({
  selector: 'app-ledger-management',
  standalone: true,
  imports: [WidgetsModule, LedgerEditComponent, ReactiveFormsModule, RouterLink],
  templateUrl: './ledger-management.component.html',
  styles: ``
})
export class LedgerManagementComponent implements PagerComponent{

  pageInfo = signal<PageInfo | undefined>(undefined)
  contents = computed(() => this.pageInfo()?.contents || [])
  editComponent = viewChild<LedgerEditComponent>(LedgerEditComponent)

  form:FormGroup

  code = signal<string | undefined>(undefined)

  constructor(builder:FormBuilder, private service:LedgerManagementService) {
    this.form = builder.group({
      type: '',
      keyword: '',
      page: 0,
      size: 10
    })

    this.search()
  }

  onLinkChange(page: number): void {
    this.form.patchValue({page: page})
    this.search()
  }

  onSizeChange(size: number): void {
    this.form.patchValue({size: size, page: 0})
    this.search()
  }

  search() {
    this.service.search(this.form.value).subscribe(result => {
      this.pageInfo.set(result)
    })
  }

  addNew() {
    this.code.set(undefined)
    this.editComponent()?.show(undefined)
  }

  edit(code:string) {
    this.code.set(code)

    this.service.findById(code).subscribe(result => {
      this.editComponent()?.show(result)
    })
  }

  save(form:any) {
    const request = this.code() != undefined ? this.service.update(this.code()!, form)
      : this.service.create(form)

    request.subscribe((_) => {
      this.editComponent()?.hide()
      this.search()
    })
  }
}
