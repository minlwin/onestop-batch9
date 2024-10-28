import { Component, viewChild } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { LedgerEditComponent } from './ledger-edit/ledger-edit.component';

@Component({
  selector: 'app-ledger-management',
  standalone: true,
  imports: [WidgetsModule, LedgerEditComponent],
  templateUrl: './ledger-management.component.html',
  styles: ``
})
export class LedgerManagementComponent {

  editComponent = viewChild<LedgerEditComponent>(LedgerEditComponent)

  addNew() {
    this.editComponent()?.show(undefined)
  }
}
