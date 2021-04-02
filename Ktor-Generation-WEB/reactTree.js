import React, { Component } from "react";
import { render } from "react-dom";
import SortableTree from "react-sortable-tree";

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      treeData: [
        {
          title: "Chicken",
          expanded: true,
          children: [{ title: "Egg" }, { title: "df" }],
        },
      ],
    };
  }

  render() {
    return (
      <div style={{ height: 500 }}>
        <SortableTree
          treeData={this.state.treeData}
          onChange={(treeData) => this.setState({ treeData })}
        />
      </div>
    );
  }
}

render(<App />, document.getElementById("tree"));
