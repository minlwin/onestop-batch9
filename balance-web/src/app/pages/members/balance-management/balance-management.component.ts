import { Component, computed, effect, input, signal } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { RouterLink } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { LedgerManagementService } from '../../../services/api/ledger-management.service';
import { PageInfo } from '../../../services/commons';
import { CommonModule } from '@angular/common';
import { PagerComponent } from '../../../widgets/pagination/pagination.component';
import { LedgerEntryService } from '../../../services/api/ledger-entry.service';

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

  constructor(builder:FormBuilder, private service:LedgerEntryService) {
    this.form = builder.group({
      type: '',
      from: '',
      to: '',
      ledger: '',
      page: 0,
      size: 10
    })

    effect(() => {
      this.form.patchValue({type: this.type()})
      this.search()
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
    this.service.search(this.type()!, this.form.value).subscribe(result => {
      this.pageInfo.set(result)
    })
  }
}
