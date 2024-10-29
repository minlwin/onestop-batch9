import { Component } from '@angular/core';
import { WidgetsModule } from '../../../widgets/widgets.module';
import { CommonModule } from '@angular/common';
import { TopLedgersComponent } from './top-ledgers/top-ledgers.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [WidgetsModule, CommonModule, TopLedgersComponent],
  templateUrl: './home.component.html',
  styles: ``
})
export class HomeComponent {

}
