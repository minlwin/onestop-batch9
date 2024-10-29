import { Component, input } from "@angular/core";
import { WidgetsModule } from "../../../../widgets/widgets.module";
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-top-ledgers',
  standalone: true,
  imports: [WidgetsModule, CommonModule],
  templateUrl: './top-ledgers.component.html',
  styles: ``
})
export class TopLedgersComponent {
  title = input.required<string>()
  ledgers = input.required<any[]>()
}
