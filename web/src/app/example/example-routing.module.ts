import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ExampleComponent } from './example.component';
import { DragDropComponent } from './drag-drop/drag-drop.component';

const routes: Routes = [
	{
		path: '',
		component: ExampleComponent
	}, {
		path: 'drag-drop',
		component: DragDropComponent
	}
];

@NgModule({
	imports: [
		RouterModule.forChild(routes)
	],
	exports: [RouterModule]
})
export class ExampleRoutingModule { }