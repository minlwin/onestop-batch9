import { Component, computed, effect, input, signal } from '@angular/core';
import { WidgetsModule } from '../../../../widgets/widgets.module';
import { LedgerEntryService } from '../../../../services/api/ledger-entry.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-balance-details',
  standalone: true,
  imports: [WidgetsModule, CommonModule],
  templateUrl: './balance-details.component.html',
  styles: ``
})
export class BalanceDetailsComponent {

  id = input.required<string>()
  details = signal<any>(undefined)
  items = computed(() => this.details()?.items as any[] || [])

  constructor(service:LedgerEntryService) {
    effect(() => {
      if(this.id()) {
        service.findById(this.id()!).subscribe(result => {
          this.details.set(result)
        })
      }
    }, {allowSignalWrites: true})
  }
}
