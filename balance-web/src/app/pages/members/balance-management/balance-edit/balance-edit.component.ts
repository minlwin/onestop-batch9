import { Component, computed, input } from '@angular/core';
import { WidgetsModule } from '../../../../widgets/widgets.module';

@Component({
  selector: 'app-balance-edit',
  standalone: true,
  imports: [WidgetsModule],
  templateUrl: './balance-edit.component.html',
  styles: ``
})
export class BalanceEditComponent {

  type = input.required<string>()
  id = input<number>()

  icon = computed(() => this.type() == 'Credit' ? 'bi-wallet' : 'bi-cart')
  subTitle = computed(() => `${this.type()} Entry`)
  title = computed(() => this.id() == undefined ? this.subTitle() : `Edit ${this.subTitle()}`)
}
