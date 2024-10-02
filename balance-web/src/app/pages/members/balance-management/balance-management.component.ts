import { Component, computed, input } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-balance-management',
  standalone: true,
  imports: [WidgetsModule, RouterLink],
  templateUrl: './balance-management.component.html',
  styles: ``
})
export class BalanceManagementComponent {

  type = input<string>()
  title = computed(() => `${this.type()} Management`)
  icon = computed(() => (this.type() == 'Credit') ? 'bi-wallet' : 'bi-cart')
}
