import { Component, computed, input, signal } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LedgerManagementService } from '../../../services/api/ledger-management.service';
import { PageInfo } from '../../../services/api';
import { CommonModule } from '@angular/common';
import { PagerComponent } from '../../../widgets/pagination/pagination.component';

@Component({
  selector: 'app-balance-management',
  standalone: true,
  imports: [WidgetsModule, RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './balance-management.component.html',
  styles: ``
})
export class BalanceManagementComponent implements PagerComponent {

  type = input<string>()
  title = computed(() => `${this.type()} Management`)
  icon = computed(() => (this.type() == 'Credit') ? 'bi-wallet' : 'bi-cart')

  pageInfo = signal<PageInfo | undefined>(undefined)
  contents = computed(() => this.pageInfo()?.contents || [])

  form:FormGroup

  constructor(builder:FormBuilder, private service:LedgerManagementService) {
    this.form = builder.group({
      type: '',
      from: '',
      to: '',
      ledger: '',
      page: 0,
      size: 10
    })
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
