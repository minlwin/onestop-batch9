import { Component, computed, input } from '@angular/core';
import { PageInfo } from '../../services/api';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent {

  pageInfo = input<PageInfo | undefined>(undefined)
  show = computed(() => (this.pageInfo()?.totalPage || 0) > 1)
}
