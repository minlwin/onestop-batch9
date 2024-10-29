import { Component, computed, signal } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { BalanceReportService } from '../../../services/api/balance-report.service';
import { PagerComponent } from '../../../widgets/pagination/pagination.component';
import { PageInfo } from '../../../services/commons';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-balance-report',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule, CommonModule],
  templateUrl: './balance-report.component.html',
  styles: ``
})
export class BalanceReportComponent implements PagerComponent{

  form:FormGroup

  pageInfo = signal<PageInfo | undefined>(undefined)
  contents = computed(() => this.pageInfo()?.contents || [])

  constructor(builder:FormBuilder,
    private service:BalanceReportService) {
    this.form = builder.group({
      from: '',
      to: '',
      ledger: '',
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
}
