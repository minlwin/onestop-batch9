import { Component, computed, EventEmitter, input, Output } from '@angular/core';
import { PageInfo } from '../../services/commons';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent {

  @Output()
  onLinkChange = new EventEmitter

  @Output()
  onSizeChange = new EventEmitter

  pageInfo = input<PageInfo | undefined>(undefined)
  links = computed(() => this.pageInfo()?.links || [])
  lastPage = computed(() => (this.pageInfo()?.totalPage || 0) - 1)
  current = computed(() => this.pageInfo()?.currentPage)

  clickLink(page:number) {
    this.onLinkChange.emit(page)
  }

  changeSize(size:any) {
    this.onSizeChange.emit(size)
  }
}

export interface PagerComponent {
  onLinkChange(page:number):void
  onSizeChange(size:number):void
}
