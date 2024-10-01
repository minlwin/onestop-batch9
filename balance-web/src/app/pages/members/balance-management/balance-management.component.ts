import { Component, computed, input } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';

@Component({
  selector: 'app-balance-management',
  standalone: true,
  imports: [WidgetsModule],
  templateUrl: './balance-management.component.html',
  styles: ``
})
export class BalanceManagementComponent {

  type = input<string>()
  title = computed(() => `${this.type()} Management`)
  icon = computed(() => (this.type() == 'Credit') ? 'bi-wallet' : 'bi-cart')
}
