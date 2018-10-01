# make datasets
# takes in the size of the dataset, width and height

import sys


FILENAME = "sample_input.txt"
OUTPUT = "sample_in.txt"
LINES = []

HEIGHT = 0
WIDTH = 0


def take_in_file(filename):
    global LINES
    with open(filename, "r") as f:
        LINES = f.readlines()


def process():
    take_in_file(FILENAME)  # Take in the initial file

    put_dimensions()  # Write the new dimensions into the new file

    make_sunlight()  # Make sunlight floats and write them to the new file

    make_trees()  # Make the trees.


def put_dimensions():
    print("Putting dimension")
    with open(OUTPUT, "a+") as f:
        f.write(str(WIDTH) + " " + str(HEIGHT) + "\n")


def make_sunlight():
    print("Making sunlight values")
    floats = LINES[1].split(" ")
    with open(OUTPUT, "a+") as f:
        for i in range(WIDTH * HEIGHT):
            f.write(floats[i] + " ")
        f.write("\n")


def make_trees():
    print("Making Trees")
    print("Make")
    treelines = []
    for i in range(3, len(LINES)):
        if i % 10000 == 0:
            print(i)
        if LINES[i] == "":
            continue
        if (check_tree(LINES[i].split(" "))):
            treelines.append(LINES[i])
    write_trees(treelines)


def write_trees(treelines):
    print("Writing trees")
    with open(OUTPUT, "a+") as f:
        f.write(str(len(treelines)) + "\n")

        for i in range(len(treelines)):
            f.write(treelines[i])


def check_tree(values):
    if(len(values) < 3):
        return False
    if (eval(values[0]) + eval(values[2]) >= WIDTH or
            eval(values[1]) + eval(values[2]) >= HEIGHT):
        return False
    return True


def main():
    global HEIGHT, WIDTH
    if len(sys.argv) < 2:
        print(sys.argv)
        sys.exit(0)
    else:
        print("Starting")
        HEIGHT = eval(sys.argv[2])
        WIDTH = eval(sys.argv[1])
        process()


if __name__ == "__main__":
    main()
